import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  user: null,
  jwt: null
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    login(state, action) {
      state.user = action.payload.user;
      state.jwt = action.payload.jwt;
    },
    logout(state) {
      state.user = null;
      state.jwt = null;
    }
  }
});

export const { login, logout } = userSlice.actions;
export default userSlice.reducer;
