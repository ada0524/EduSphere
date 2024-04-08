import { useNavigate } from "react-router-dom";
import UserHomePage from "../pages/UserHomePage";
import AdminHomePage from "../pages/admin/AdminHomePage";
import { useEffect, useState } from "react";

const HomeGuard = () => {
    const navigate = useNavigate();

    const [userType, setUserType] = useState<string>('');

    useEffect(() => {
        const token: string | null = localStorage.getItem('token');
        if (token !== null) {
            //parse the token
            const tokenData = JSON.parse(atob((token as string).split('.')[1]));
            setUserType(tokenData.type);
        } else {
            navigate('/login');

        }
    }, [navigate]);
    // Conditional rendering based on role
    if (userType === 'user') {
        return <UserHomePage />;
    } else if (userType === 'admin') {
        return <AdminHomePage />;
    }

    // Render null or a loading spinner while checking role
    return null;
}

export default HomeGuard;