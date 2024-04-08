import { backendUrl, getUserByIdPath } from "./url";

export interface User {
    userId: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    active: boolean;
    dateJoined: string;
    type: string;
    profileImageUrl: string;
    emailVerified: boolean;
    tokenExpiry: string | null;
}

export const getUserById = (userId: string): Promise<User> => {
  const getUserByIdUrl: string = backendUrl + getUserByIdPath(userId);

  const token: string | null = localStorage.getItem("token");

  return fetch(getUserByIdUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + token,
    },
  }).then((response) => {
    if (response.ok) {
        console.log(response.json());
      return response.json();
    }
    throw new Error("Failed to fetch user by id");
  });
}