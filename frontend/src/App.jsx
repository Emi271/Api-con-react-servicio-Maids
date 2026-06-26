import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import MaidList from './pages/MaidList';
import MaidForm from './pages/MaidForm';
import ShiftList from './pages/ShiftList';
import './App.css';

function App() {
  return (
    <Router>
      <div className="app-layout">
        <nav className="sidebar">
          <h2>ApiCompleja UI</h2>
          <ul>
            <li><Link to="/">Dashboard</Link></li>
            <li><Link to="/maids">Gestión de Maids</Link></li>
            <li><Link to="/shifts">Gestión de Turnos</Link></li>
          </ul>
        </nav>
        
        <main className="main-content">
          <Routes>
            <Route path="/" element={<h1>Bienvenido al Panel de Control</h1>} />
            <Route path="/maids" element={<MaidList />} />
            <Route path="/maids/new" element={<MaidForm />} />
            <Route path="/shifts" element={<ShiftList />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;