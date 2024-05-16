import Api from './Api';

export default {
  getAll() {
    return Api.get('/contracts');
  },
  get(id) {
    return Api.get('/contracts/' + id);
  },
  create(data) {
    return Api.post('/contracts', data);
  },
  update(id, data) {
    return Api.put('/contracts/' + id, data);
  },
  delete(id) {
    return Api.delete('/contracts/' + id);
  }
};
