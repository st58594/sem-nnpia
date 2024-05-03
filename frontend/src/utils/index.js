import { store } from "../store";

export const isAdmin = () => {
  return !!store.getState().user?.user && store.getState().user.user.role === 'ADMIN';
};
