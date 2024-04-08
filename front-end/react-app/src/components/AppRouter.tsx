import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import RegisterPage from "../pages/RegisterPage";
import UserProfilePage from "../pages/UserProfilePage";
import ContactAdminPage from "../pages/ContactAdminPage";
import PostDetailPage from "../pages/PostDetailPage";
import UserManagementPage from "../pages/admin/UserManagementPage";
// import PrivateRoute from "../components/PrivateRoute"; // TODO: a PrivateRoute component for auth
import CreatePostPage from "../pages/CreatePostPage";
import Layout from "./Layout";
import AdminGuard from "./AdminGuard";
import HomeGuard from "./HomeGuard";

const AppRouter: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route element={<Layout />}>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/contact-admin" element={<ContactAdminPage />} />

          <Route path="/home" element={<HomeGuard />} />
          <Route path="/create-post" element={<CreatePostPage />} />
          <Route path="/profile" element={<UserProfilePage />} />
          <Route path="/posts/:id" element={<PostDetailPage />} />
          <Route element={<AdminGuard />}>
            <Route path="/admin/user-management" element={<UserManagementPage />} />
          </Route>
          <Route path="*" element={<HomeGuard />} />
        </Route>
      </Routes>
    </Router>
  );
};

export default AppRouter;
