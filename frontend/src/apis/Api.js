import axios from 'axios';
import config from '../config';
import { store } from '../store';
import { logout } from '../store/slices/userSlice';
import router from '../router';

// CORS: zabraňuje nepovolené, nechtěné interakce s aplikacemi na jiných portech, doménách.
//  Falešné requesty z jiných aplikací.
const Api = axios.create({
  baseURL: config.api_base_url,
  withCredentials: true,
  headers: {
    common: {
      'X-Requested-With': 'XMLHttpRequest',
    }
  }
});

Api.interceptors.request.use(request => {
  if (store.getState().user.jwt) {
    request.headers.Authorization = `Bearer ${store.getState().user.jwt}`;
  }
  return request;
});

Api.interceptors.response.use(
  response => response,
  error => {
    const status = error.response ? error.response.status : null;
    if (status === 401) {
      store.dispatch(logout());
      router.navigate('/login');
    }
    return Promise.reject(error);
  }
);

export default Api;
