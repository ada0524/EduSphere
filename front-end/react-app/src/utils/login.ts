// Business logic

import { backendUrl, loginPath} from "./url";

export interface LoginData {
    email: string;
    password: string;
}
export const login = (loginData:LoginData) => {
    // backend endpoint for login
    const loginUrl:string = backendUrl + loginPath;

    return fetch(loginUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginData),
    })
    .then((response) => {
        //check if the status field of response body is 'SUCCESS'
        if(response.status < 300 && response.status >= 200) {
            return response.json();
        }
        throw new Error('Login request failed to sent.');
    });   
}