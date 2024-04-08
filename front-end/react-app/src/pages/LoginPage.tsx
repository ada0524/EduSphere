import React, { useState, FormEvent } from 'react';
import { LoginData, login } from '../utils/login';
import { useNavigate } from 'react-router-dom';
const LoginForm: React.FC = () => {
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const navigate = useNavigate();
  // On submit, call the login function from the login module
  const onSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // console.log('Login submitted with email:', email, 'and password:', password);
    const loginData: LoginData = { email, password };
    // console.log(loginData);
    login(loginData)
      .then(data => {
        // console.log('Login successful', data);

        // Store the token in local storage
        localStorage.setItem('token', data.token);
        // Redirect to the user home page
        navigate('/home');
      })
      .catch(() => {
        alert('Login failed. Wrong email or password.');
      });
  };

  return (
    <form
      onSubmit={onSubmit}
      style={{ display: 'flex', flexDirection: 'column', width: '50%', margin: 'auto' }}>
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <button type="submit">Log In</button>
      <button type="button" onClick={() => navigate('/register')}>Register</button>
    </form>
  );
};

export default LoginForm;
