package cn.gdsdxy.s15vueshopping.config;

import cn.gdsdxy.s15vueshopping.util.FwResult;
//import cn.gdsdxy.s17jwt.util.FwResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 @RestControllerAdvice作用
 当我们在类上使用@RestControllerAdvice注解时，它相当于同时使用了@ControllerAdvice和@ResponseBody。这意味着被@RestControllerAdvice注解标记的类将被视为全局异常处理器，并且异常处理方法的返回值将以JSON格式直接写入响应体中。
 通过在@RestControllerAdvice类中定义异常处理方法，我们可以捕获和处理控制器中抛出的异常，提供自定义的异常处理逻辑，以及返回适当的响应给客户端。这样可以统一处理应用程序中的异常情况，提高代码的可维护性和可读性。
 优点：将 Controller 层的异常和数据校验的异常进行统一处理，减少模板代码，减少编码量，提升扩展性和可维护性。
 缺点：只能处理 Controller 层未捕获（往外抛）的异常，对于 Interceptor（拦截器）层的异常，Spring 框架层的异常，就无能为力了。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)//返回给前端的状态码
    @ExceptionHandler({Exception.class})//当控制器中产生Exception异常时执行下面的方法
    public FwResult businessException(Exception e) {
        log.error("BusinessException 异常",e);
        return FwResult.failedMsg("系统内部错误,请稍后重试");
    }
}
