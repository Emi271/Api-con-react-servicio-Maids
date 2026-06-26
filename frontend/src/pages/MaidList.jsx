import { useEffect, useState } from 'react';
import { getMaids } from '../services/maid.service';
import { Link } from 'react-router-dom';

export default function MaidList() {
    const [maids, setMaids] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getMaids()
            .then(data => {
                setMaids(data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Error al cargar maids", err);
                setLoading(false);
            });
    }, []);

    if (loading) return <div>Cargando...</div>;

    return (
        <div className="page-container">
            <div className="header-actions">
                <h2>Lista de Maids</h2>
                <Link to="/maids/new" className="btn btn-primary">Registrar Nueva Maid</Link>
            </div>
            
            <table className="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Alias</th>
                        <th>Nombre Real</th>
                        <th>Edad</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {maids.map(maid => (
                        <tr key={maid.id}>
                            <td>{maid.id}</td>
                            <td><strong>{maid.alias}</strong></td>
                            <td>{maid.realName}</td>
                            <td>{maid.age}</td>
                            <td>
                                <button className="btn btn-secondary">Ver Detalles</button>
                            </td>
                        </tr>
                    ))}
                    {maids.length === 0 && (
                        <tr>
                            <td colSpan="5">No hay maids registradas.</td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}