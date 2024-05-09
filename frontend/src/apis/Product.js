import Api from './Api';

export default {
  getAll(page, pageSize, filter, sort) {
    return Api.get('/product' +
        '?page='+page +
        '&pageSize='+ pageSize +
        "&" + new URLSearchParams(filter).toString() +
        (sort ? '&sort='+ sort : ''));
  },
  get(id) {
    return Api.get('/product/' + id);
  },
  create(data) {
    return Api.post('/product', data);
  },
  update(id, data) {
    return Api.put('/product/' + id, data);
  },
  delete(id) {
    return Api.delete('/product/' + id);
  }
};
