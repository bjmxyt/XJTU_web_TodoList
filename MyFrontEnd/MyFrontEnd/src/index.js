import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import TodoList from './TodoList';
import * as serviceWorker from './serviceWorker';

ReactDOM.render(
  <React.StrictMode>
    <h1> 待办事项列表 </h1>
    <TodoList />
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
