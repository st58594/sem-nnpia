import React from 'react'
import ReactDOM from 'react-dom/client'
import { Provider } from 'react-redux';
import 'bootstrap/dist/css/bootstrap.css'
import { persistor, store } from './store';
import router from './router';
import {PersistGate} from "redux-persist/integration/react";
import {RouterProvider} from "react-router-dom";

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <Provider store={store}>
          <PersistGate loading={null} persistor={persistor}>
              <RouterProvider router={router} />
          </PersistGate>
      </Provider>
  </React.StrictMode>,
)
