export const getUserIdFromJwt = (token: string) => {
  try {
    const base64Url = token.split('.')[1]; // Get the payload part of the token
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Convert base64Url to base64
    const payload = decodeURIComponent(atob(base64).split('').map((c) => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join('')); 

    const jwtPayload = JSON.parse(payload);

    if (jwtPayload && jwtPayload.userId) {
      return jwtPayload.userId;
    }

    return null; 
  } catch (error) {
    console.error("Error decoding JWT:", error);
    return null; 
  }
}