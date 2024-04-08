// ContactAdminPage.tsx
import React, { useState } from 'react';
import { backendUrl} from "../utils/url";
import { useNavigate } from 'react-router-dom';

const ContactAdminPage: React.FC = () => {
  const [formData, setFormData] = useState({
    subject:'',
    email: '',
    text: ''
});
const [submitted, setSubmitted] = useState(false);
const navigate = useNavigate();

const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
  const { name, value } = e.target;
  setSubmitted(false);
  setFormData((prevState: any) => ({
      ...prevState,
      [name]: value
  }));
};

const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
  e.preventDefault();
  const token = localStorage.getItem('token');
  
  const endpoint:string = "http://localhost:9000/message-service/users/contactus";
    console.log(JSON.stringify(formData));
  return fetch(endpoint, {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token,
    },
    body: JSON.stringify(formData),
}).then((response) => {
    if(response.ok) {
        return response.json();
    }
    else if(response.status == 401){
      navigate('/login');
    }
    throw new Error('Failed to submit');
})
.then(data => {
    setSubmitted(true);
    cleanUp();
    console.log('Succesed', data);
  })
  .catch(error => {
    console.error('An error occurred:', error);
  });

};

const cleanUp = () =>{
    setFormData({
        subject:'',
        email: '',
        text: ''
    });
}

  return (
    <div>
        <h2>Contact Us</h2>
        <form onSubmit={handleSubmit}>
            <div>
            <label htmlFor="subject">Subject:</label>
                <input
                    id="subject"
                    name="subject"
                    value={formData.subject}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label htmlFor="email">Email Address:</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                />
            </div>
            <div>
                <label htmlFor="message">Message:</label>
                <textarea
                    id="text"
                    name="text"
                    value={formData.text}
                    onChange={handleChange}
                    required
                />
            </div>
            {submitted && <h3>Succeed!</h3>}
            <button type="submit">Submit</button>
        </form>
    </div>
);
};

export default ContactAdminPage;
