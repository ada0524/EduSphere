import React, { useState, useEffect } from 'react';
import { getUserIdFromJwt } from '../utils/getUserIdFromJWT';
import { useNavigate } from 'react-router-dom';
import HistoryList from './HistoryPage';

interface UserInfo {
  firstName: number;
  lastName: string;
  profileImageUrl: string;
  dateJoined: string;
}

const UserDisplay: React.FC = () => {
  const [user, setUser] = useState<UserInfo | null>(null);
  const [imageUrl, setImageUrl] = useState<string>('');

  const token: string | null = localStorage.getItem("token");
  let userId:number = 0;
  if (token == null) {
    //should direct back to login package
    const navigate = useNavigate()
    navigate("/login")
  } else {
    userId = getUserIdFromJwt(token);
  }



  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await fetch(`http://localhost:9000/user-service/getUserById?id=${userId}`, {
          headers: {
              /* 'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoUmVnQEF1dGhSZWcuY29tIiwidXNlcklkIjo2LCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiVXNlciJ9LHsiYXV0aG9yaXR5IjoiYWN0aXZlIn1dfQ.TWTDB-veNbx4xpxmJgA1eekfUhUOuzL44-P3FWFz1OY" */
              'Authorization': "Bearer " + token
          }
      });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const {data}: {data: UserInfo} = await response.json();
        setUser(data);
      } catch (err: any) {
        console.error('Error fetching user info:', err);
      }
    };
    fetchUser();

    const fetchImage = async () => {
      try {
        const response = await fetch(`http://localhost:9000/file-service/file/user/profile?key=${user?.profileImageUrl}`, {
          headers: {
              /* 'Authorization': "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoUmVnQEF1dGhSZWcuY29tIiwidXNlcklkIjo2LCJwZXJtaXNzaW9ucyI6W3siYXV0aG9yaXR5IjoiVXNlciJ9LHsiYXV0aG9yaXR5IjoiYWN0aXZlIn1dfQ.TWTDB-veNbx4xpxmJgA1eekfUhUOuzL44-P3FWFz1OY" */
              'Authorization': "Bearer " + token
          }
      });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const {data}: {data: string} = await response.json();
        setImageUrl(data);
      } catch (err: any) {
        console.error('Error fetching user info:', err);
      }
    };

    fetchImage();
  }, []); 

  if (!user) return <div>No user data found.</div>;

  return (
    <div>
      <h2>User Information</h2>
      <img src={imageUrl} alt="Description" />;
      <p><strong>First Name:</strong> {user.firstName}</p>
      <p><strong>Last Name:</strong> {user.lastName}</p>
      <p><strong>Registered at:</strong> {user.dateJoined}</p>
      <h2>View History</h2>
      <HistoryList userId={userId} />
    </div>
  );
};

export default UserDisplay;