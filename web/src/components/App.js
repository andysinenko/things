import './App.css';
import { AuthProvider } from './auth/AuthProvider';
import SignIn from './signin/SignIn';

function App() {
    return (
        <div className="Container">
            <AuthProvider>
                <SignIn />
                <h3>App</h3>
            </AuthProvider>
        </div>
    );
}

export default App;