import { store } from "../store";

export const hasAuthority = (role) => {
  return !!store.getState().user?.user && store.getState().user.user.roles.some(item => item.role === role.toUpperCase());
}