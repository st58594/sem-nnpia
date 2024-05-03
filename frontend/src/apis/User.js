import Api from './Api';

export default {
  getAll() {
    return Api.get('/user');
  },
  get(id) {
    return Api.get('/user/' + id);
  },
  create(data) {
    return Api.post('/user', data);
  },
  update(id, data) {
    return Api.put('/user/' + id, data);
  },
  delete(id) {
    return Api.delete('/user/' + id);
  }
};
