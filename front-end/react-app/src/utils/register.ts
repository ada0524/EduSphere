import { backendUrl, registerPath } from "./url";

export interface RegisterData {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    verificationCode: string;
    emailVerified: 0 | 1;
}

export const register = (registerData:RegisterData) => {
    // backend endpoint for registration
    const registerUrl:string = backendUrl + registerPath;

    return fetch(registerUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(registerData),
    }).then((response) => {
        if(response.status < 300 && response.status >= 200) {
            return response.json();
        }
        // If the email verification failed, alert the user
        // e.g. according to the status code...
        throw new Error('Registration failed');
    })
    // @TODO Not good practice, need to be modified
    // The backend should return a failure status code, not a status field in the response body  
    .then(data => {
        if(data.status != 'SUCCESS') {
            throw new Error(data.message);
        }
        return data;
    })
    .catch(error => {
    console.error('An error occurred:', error);
    });
}