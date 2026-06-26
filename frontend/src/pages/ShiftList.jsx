import { useEffect, useState } from 'react';
import { getShifts } from '../services/shift.service';

export default function ShiftList() {
    const [shifts, setShifts] = useState([]);

    useEffect(() => {
        getShifts().then(setShifts).catch(console.error);
    }, []);

    return (
        <div className="page-container">
            <h2>Gestión de Turnos</h2>
            <table className="data-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Fecha</th>
                        <th>Inicio</th>
                        <th>Fin</th>
                        <th>Maid (Alias)</th>
                        <th>Zona</th>
                    </tr>
                </thead>
                <tbody>
                    {shifts.map(shift => (
                        <tr key={shift.id}>
                            <td>{shift.id}</td>
                            <td>{shift.shiftDate}</td>
                            <td>{shift.startTime}</td>
                            <td>{shift.endTime}</td>
                            <td><strong>{shift.maidAlias}</strong></td>
                            <td>{shift.zoneName}</td>
                        </tr>
                    ))}
                    {shifts.length === 0 && (
                        <tr><td colSpan="6">No hay turnos registrados.</td></tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}