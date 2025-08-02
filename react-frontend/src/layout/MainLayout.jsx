// src/layout/MainLayout.jsx
import React from 'react';
import Menubar from '../component/Menubar';
import { Outlet } from 'react-router-dom';

const MainLayout = () => {
  return (
    <>
      <Menubar />
      <div style={{ paddingTop: '80px' }}> {/* to avoid overlap with fixed navbar */}
        <Outlet /> {/* This renders the child routes */}
      </div>
    </>
  );
};

export default MainLayout;
