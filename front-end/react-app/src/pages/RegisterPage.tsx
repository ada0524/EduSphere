// RegisterPage.tsx
// the user information: firstname, lastname, email, password
// TODO: the email service: send email containing verification code
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { RegisterData } from '../utils/register';

const RegisterPage: React.FC = () => {
  const [user, setUser] = useState({
    firstname: '',
    lastname: '',
    email: '',
    password: '',
    confirmPassword: '',
    verificationCode: '',
    emailVerified: '',
  });

  const [buttonText, setButtonText] = useState('Send Verification Code');
  const [emailVerified, setEmailVerified] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    if (emailVerified) {
      setButtonText('Email Verified');
    }
  }, [emailVerified]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

  const handleSendEmail = async () => {
    try {
      const response = await fetch('http://localhost:9000/email-service/verificationRequest', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          firstName: user.firstname,
          lastName: user.lastname,
          email: user.email,
          password: user.password,
        }),
      });
      if (response.ok) {
        const data = await response.text();
        alert(data);
        setButtonText('Verify Code');
      } else {
        alert('Failed to send email');
      }
    } catch (error) {
      console.error('Error sending email:', error);
      alert('Error sending email');
    }
  };

  const handleVerifyCode = async () => {
    try {
      const response = await fetch(`http://localhost:9000/email-service/verify?inputCode=${user.verificationCode}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          firstName: user.firstname,
          lastName: user.lastname,
          email: user.email,
          password: user.password,
        }),
      });
      if (response.ok) {
        const data = await response.text();
        alert(data);
        setEmailVerified(true);
      } else {
        alert('Verification code is incorrect');
      }
    } catch (error) {
      console.error('Error verifying code:', error);
      alert('Error verifying code');
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // Confirm that the passwords match
    if (user.password !== user.confirmPassword) {
      alert('Passwords do not match');
      return;
    } else {
      // Call the register function here
      const registerData: RegisterData = {
        firstName: user.firstname,
        lastName: user.lastname,
        email: user.email,
        password: user.password,
        verificationCode: user.verificationCode,
        emailVerified: emailVerified ? 1 : 0,
      };
      const response = await fetch('http://localhost:9000/auth-service/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(registerData),
      });
      const responseJson = await response.json(); // 解析响应的 JSON 数据

      if (response.ok) {
        // Registration successful
        if (responseJson.status === 'SUCCESS') {
          window.confirm('Registration successful! You can now log in.');
          navigate('/login');
        } else {
          alert(responseJson.message);
        }
      } else {
        // Registration failed
        alert('Registration failed');
      }
    }
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{ display: 'flex', flexDirection: 'column', width: '50%', margin: 'auto' }}
    >
      <input
        type="text"
        name="firstname"
        value={user.firstname}
        onChange={handleChange}
        placeholder="First Name"
        required
      />
      <input
        type="text"
        name="lastname"
        value={user.lastname}
        onChange={handleChange}
        placeholder="Last Name"
        required
      />
      <input
        type="email"
        name="email"
        value={user.email}
        onChange={handleChange}
        placeholder="Email"
        required
      />
      <input
        type="password"
        name="password"
        value={user.password}
        onChange={handleChange}
        placeholder="Password"
        required
      />
      <input
        type="password"
        name="confirmPassword"
        value={user.confirmPassword}
        onChange={handleChange}
        placeholder="Confirm Password"
        required
      />
      <div>
        <input
          type="text"
          name="verificationCode"
          value={user.verificationCode}
          onChange={handleChange}
          placeholder="Verification Code"
        />
        <button type="button" onClick={buttonText === 'Send Verification Code' ? handleSendEmail : handleVerifyCode}>
          {buttonText}
        </button>
      </div>
      <button type="submit">Register</button>
    </form>
  );
};

export default RegisterPage;

