# -*- coding: utf-8 -*-
from odoo import models, fields

class HospitalEspecialidad(models.Model):
    """
    Modelo que representa las especialidades médicas del hospital.

    Permite categorizar a los médicos y asignarles un número de consulta 
    físico dentro del centro.

    Attributes:
        name (fields.Char): Nombre de la especialidad (Max. 40 caracteres).
        consulta (fields.Selection): Menú desplegable para asignar la sala de consulta (1 a 5).
        medico_ids (fields.One2many): Relación inversa con los médicos asignados a esta especialidad.
    """
    _name = 'hospital.especialidad'
    _description = 'Especialidades del Hospital'

    name = fields.Char(string='Nombre', required=True, size=40)
    consulta = fields.Selection([
        ('consulta1', 'Consulta 1'),
        ('consulta2', 'Consulta 2'),
        ('consulta3', 'Consulta 3'),
        ('consulta4', 'Consulta 4'),
        ('consulta5', 'Consulta 5'),
    ], string='Consultas', default='consulta1')
    medico_ids = fields.One2many('hospital.medico', 'especialidad_id', string='Médicos en esta especialidad')


class HospitalMedico(models.Model):
    """
    Modelo que gestiona el personal facultativo del hospital.

    Almacena los datos personales, de contacto y perfil profesional de cada médico.
    Se relaciona bidireccionalmente con las especialidades y las citas de los pacientes.

    Attributes:
        name (fields.Char): Nombre y apellidos completos del médico (Max. 40 caracteres).
        domicilio (fields.Char): Dirección postal de residencia.
        telefono (fields.Char): Número de teléfono de contacto (9 dígitos).
        fecha_ingreso (fields.Date): Fecha de contratación o alta en el sistema.
        image (fields.Image): Fotografía de perfil, utilizada para renderizar la vista Kanban.
        especialidad_id (fields.Many2one): Relación con el modelo hospital.especialidad.
        cita_ids (fields.One2many): Relación inversa con el modelo hospital.cita.
    """
    _name = 'hospital.medico'
    _description = 'Médicos del Hospital'

    name = fields.Char(string='Nombre y Apellidos', required=True, size=40)
    domicilio = fields.Char(string='Domicilio', required=True, size=40)
    telefono = fields.Char(string='Teléfono', required=True, size=9)
    fecha_ingreso = fields.Date(string='Fecha de Ingreso', required=True)
    image = fields.Image(string="Foto de Perfil")
    especialidad_id = fields.Many2one('hospital.especialidad', string='Especialidad', required=True)
    cita_ids = fields.One2many('hospital.cita', 'medico_id', string='Citas Asignadas')


class HospitalCita(models.Model):
    """
    Modelo transaccional para la programación de citas médicas.

    Vincula a un paciente (entidad de texto) con un facultativo específico 
    en una fecha y hora determinadas.

    Attributes:
        paciente (fields.Char): Nombre y apellidos del paciente que solicita la cita.
        medico_id (fields.Many2one): Médico asignado para pasar la consulta.
        fecha_cita (fields.Datetime): Marca de tiempo (fecha y hora) programada para la visita.
    """
    _name = 'hospital.cita'
    _description = 'Citas de Pacientes'

    paciente = fields.Char(string='Paciente (Nombre y Apellido)', required=True, size=40)
    medico_id = fields.Many2one('hospital.medico', string='Médico', required=True)
    fecha_cita = fields.Datetime(string='Fecha y hora de la cita', required=True)