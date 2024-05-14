import './App.css';
import AppHeader from './app-header';
import Books from './books';
import Tools from './tools';
import Places from './places';

function App() {
  return (
    <div className="App">
      <AppHeader />
      <Places></Places>
      <Books></Books>
      <Tools></Tools>
    </div>
  );
}

export default App;