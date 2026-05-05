{
    'name': 'hospital',
    'version': '1.0',
    'summary': 'Módulo para la gestión de especialidades, médicos y citas',
    'description': 'Permite gestionar el registro de pacientes, asignación de médicos y programación de citas.',
    'author': 'José Manuel Sánchez Rosal',
    'depends': ['base'],
    'data': [
        'views/reports.xml',
        'security/security.xml',
        'security/ir.model.access.csv',
        'views/views.xml',
    ],
    'installable': True,
    'application': True,
    'icon': '/hospital/static/description/icon.png',
}