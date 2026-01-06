package cn.gdsdxy.s15vueshopping.service;

import cn.gdsdxy.s15vueshopping.entity.Address;
import cn.gdsdxy.s15vueshopping.mapper.AddressMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AddressService {

    private AddressMapper addressMapper;
    @Resource
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    // 获取用户的默认地址
    public Address getUserDefaultAddress(int userId){//获取某一个用户的默认地址
        QueryWrapper<Address> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("is_default",1);
        List<Address> addressList = addressMapper.selectList(wrapper);//调用selectList()方法的返回值都是List类型
        return addressList.get(0);//我们只要得到用户的默认地址，不需要多个地址，所以通过get(0)获取list中的第一条记录
    }

    // 获取用户所有地址列表
    public List<Address> getAddressList(int userId) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("is_default"); // 默认地址排前面
        return addressMapper.selectList(wrapper);
    }

    // 新增地址
    @Transactional
    public void addAddress(Address address) {
        // 如果新增的地址是默认地址，先把该用户其他地址设为非默认
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            clearDefaultAddress(address.getUserId());
        }
        addressMapper.insert(address);
    }

    // 修改地址
    @Transactional
    public void updateAddress(Address address) {
        // 如果修改为默认地址，先把其他设为非默认
        if (Boolean.TRUE.equals(address.getIsDefault())) {
            clearDefaultAddress(address.getUserId());
        }
        addressMapper.updateById(address);
    }

    // 删除地址
    public void deleteAddress(int addressId) {
        addressMapper.deleteById(addressId);
    }

    // 辅助方法：将某用户的所有地址设为非默认
    private void clearDefaultAddress(int userId) {
        UpdateWrapper<Address> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId).set("is_default", 0);
        addressMapper.update(null, wrapper);
    }
}
