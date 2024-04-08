import { backendUrl, getAllUsersPath, updateUserStatusPath } from "./url";

export interface User {
  userId: string;
  fullName: string;
  email: string;
  dateJoined: string;
  type: string;
  status: string;
}

// first half: return the array of all users that will be displayed on the user management page
export const getAllUsers = () => {
  const getUsersUrl: string = backendUrl + getAllUsersPath;

  const token: string | null = localStorage.getItem("token");

  // attach the token to the request header
  return fetch(getUsersUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
  }).then((response) => {
    if (response.ok) {
      return response.json();
    }
    throw new Error("Failed to fetch users");
  });
};



export const updateUserStatus = (
  userId: string,
  newStatus: string
): Promise<void> => {
  const updateUserStatusUrl: string = backendUrl + updateUserStatusPath;

  const token: string | null = localStorage.getItem("token");

  return fetch(updateUserStatusUrl, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
    body: JSON.stringify({
      userId,
      isActive: newStatus === "Active" ? true : false,
    }),
  }).then((response) => {
    if (response.ok) {
      return; // 返回空表示成功
    }
    throw new Error("Failed to update user status");
  });
};
