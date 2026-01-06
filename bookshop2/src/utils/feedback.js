export const toast = (message, type = 'info') => {
  if (!message) return
  const event = new CustomEvent('app-toast', { detail: { message, type } })
  window.dispatchEvent(event)
}
