# -*- coding: utf-8 -*-
# from odoo import http


# class Esqueleto(http.Controller):
#     @http.route('/esqueleto/esqueleto/', auth='public')
#     def index(self, **kw):
#         return "Hello, world"

#     @http.route('/esqueleto/esqueleto/objects/', auth='public')
#     def list(self, **kw):
#         return http.request.render('esqueleto.listing', {
#             'root': '/esqueleto/esqueleto',
#             'objects': http.request.env['esqueleto.esqueleto'].search([]),
#         })

#     @http.route('/esqueleto/esqueleto/objects/<model("esqueleto.esqueleto"):obj>/', auth='public')
#     def object(self, obj, **kw):
#         return http.request.render('esqueleto.object', {
#             'object': obj
#         })
