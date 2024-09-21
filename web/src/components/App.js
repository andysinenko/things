import './App.css';
import AppHeader from './app-header';
import Books from './books';
import Tools from './tools';
import Places from './places';
import Dashboard from './dashboard/dashbard';

function App() {
  return (
    <div className="container-fluid">
      <AppHeader />
      <Dashboard />
      <Places />
      <Books />
      <Tools />
    </div>
  );
}

export default App;