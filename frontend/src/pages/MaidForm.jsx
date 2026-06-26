import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createMaid, getSkills } from '../services/maid.service';

export default function MaidForm() {
    const navigate = useNavigate();
    const [skillsList, setSkillsList] = useState([]);
    const [error, setError] = useState('');
    
    // Estado inicial basado en MaidRequestDTO
    const [formData, setFormData] = useState({
        realName: '',
        alias: '',
        age: 18,
        skillIds: []
    });

    useEffect(() => {
        // Cargar skills para los checkboxes
        getSkills().then(setSkillsList).catch(console.error);
    }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({
            ...prev,
            [name]: name === 'age' ? parseInt(value) : value
        }));
    };

    const handleSkillChange = (skillId) => {
        setFormData(prev => {
            const newSkills = prev.skillIds.includes(skillId)
                ? prev.skillIds.filter(id => id !== skillId)
                : [...prev.skillIds, skillId];
            return { ...prev, skillIds: newSkills };
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        
        try {
            await createMaid(formData);
            navigate('/maids');
        } catch (err) {
            setError(err.response?.data?.message || "Error al crear la maid. Verifica los datos.");
        }
    };

    return (
        <div className="form-container">
            <h2>Registrar Nueva Maid</h2>
            {error && <div className="error-message">{error}</div>}
            
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Nombre Real:</label>
                    <input 
                        type="text" 
                        name="realName" 
                        value={formData.realName} 
                        onChange={handleChange} 
                        required 
                    />
                </div>

                <div className="form-group">
                    <label>Alias:</label>
                    <input 
                        type="text" 
                        name="alias" 
                        value={formData.alias} 
                        onChange={handleChange} 
                        required 
                    />
                </div>

                <div className="form-group">
                    <label>Edad (Mínimo 18):</label>
                    <input 
                        type="number" 
                        name="age" 
                        min="18" 
                        value={formData.age} 
                        onChange={handleChange} 
                        required 
                    />
                </div>

                <div className="form-group">
                    <label>Habilidades (Selecciona al menos una):</label>
                    <div className="checkbox-group">
                        {skillsList.map(skill => (
                            <label key={skill.id} className="checkbox-label">
                                <input 
                                    type="checkbox"
                                    checked={formData.skillIds.includes(skill.id)}
                                    onChange={() => handleSkillChange(skill.id)}
                                />
                                {skill.name}
                            </label>
                        ))}
                    </div>
                </div>

                <button type="submit" className="btn btn-primary" disabled={formData.skillIds.length === 0}>
                    Guardar
                </button>
            </form>
        </div>
    );
}