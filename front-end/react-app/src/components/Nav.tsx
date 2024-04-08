import { Link } from 'react-router-dom';
import { logout } from '../utils/logout';

//TODO: adjust the content according to current page
const Nav = () => {
    // get token from local storage
    const token: string | null = localStorage.getItem('token');
    // not signed in
    if (token === null) {
        return (
            <nav>
                <Link to='/login'>Login</Link>
                <Link to='/register'>Register</Link>
                <Link to='/contact-admin'>Contact Admin</Link>
            </nav>
        );
    }
    // TODO: the interface for the JWT token
    // TODO: check if the token is valid or have the type field
    // parse the token to get the role
    const tokenData = JSON.parse(atob((token as string).split('.')[1]));
    // get the username from the token
    const userType: string = tokenData.type;
    if (userType === 'user') {
        return (
            <nav>
                <Link to='/home'>Home</Link> <br />
                <Link to='/create-post'>Create Post</Link> <br />
                <Link to='/profile'>Profile</Link> <br />
                <Link to='/contact-admin'>Contact Admin</Link><br />
                <Link to='/login' onClick={logout}>Logout</Link>
            </nav>
        );
    } else if (userType === 'admin') {
        return (
            <nav>
                <Link to='/admin/user-management'>User Management</Link> <br />
            </nav>
        );
    }
};

export default Nav;
