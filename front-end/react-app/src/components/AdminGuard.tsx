import { Outlet, useNavigate } from "react-router-dom";

const AdminGuard = () => {
    const navigate = useNavigate();

    const token:string | null = localStorage.getItem('token');
    if(token !== null) {
        const tokenData = JSON.parse(atob((token as string).split('.')[1]));
        const userType: string = tokenData.type;
        if (userType === 'admin') {
            return (
                <Outlet />
            );
        }
    }
    alert('You are not authorized to view this page');
    // Redirect to the last page
    navigate(-1);
}

export default AdminGuard;
