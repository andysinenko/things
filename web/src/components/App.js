import './App.css';
import AppHeader from './app-header';
/*import Books from './books';
import Tools from './tools';
import Places from './places';
import Dashboard from './dashboard/dashbard';*/
import Admin from './admin';

function App() {
  return (
    <div className="Container">
        <AppHeader />
        <Admin />
      {/*
      <Dashboard />
      <Places />
      <Books />
      <Tools />*/}
    </div>
  );
}

export default App;