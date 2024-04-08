import React, { useState, useEffect } from "react";

import { getAllUsers, User, updateUserStatus } from "../../utils/getUsers";

const UserManagementPage: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);

  useEffect(() => {
    getAllUsers()
      .then((users: User[]) => {
        setUsers(users);
      })
      .catch((error) => {
        console.error("Failed to fetch users:", error);
      });
  }, []);

  const handleStatusChange = (userId: string, newStatus: string) => {
    // 更新用户状态
    updateUserStatus(userId, newStatus).then(() => {
      // 更新用户列表
      setUsers((prevUsers) =>
        prevUsers.map((user) =>
          user.userId === userId ? { ...user, status: newStatus } : user
        )
      );
    });
  };

  return (
    <div>
      <h1>User Management</h1>
      <table>
        <thead>
          <tr>
            <th>User ID</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Date Joined</th>
            <th>Type</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.userId}>
              <td>{user.userId}</td>
              <td>{user.fullName}</td>
              <td>{user.email}</td>
              <td>{user.dateJoined}</td>
              <td>{user.type}</td>
              <td>
                {user.status === "Active" ? (
                  <button
                    onClick={() => handleStatusChange(user.userId, "Banned")}
                  >
                    Ban
                  </button>
                ) : (
                  <button
                    onClick={() => handleStatusChange(user.userId, "Active")}
                  >
                    Activate
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserManagementPage;
