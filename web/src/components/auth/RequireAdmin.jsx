import { useSelector } from 'react-redux';
import { Navigate } from 'react-router-dom';

function hasAdminRole(authorities) {
    return authorities?.some(a => a.name === 'ADMIN') ?? false;
}

export function RequireAdmin({ children }) {
    const { user, loading } = useSelector(state => state.userReducer);

    if (loading) return null;
    if (!hasAdminRole(user?.authorities)) {
        return <Navigate to="/dashboard" replace />;
    }
    return children;
}