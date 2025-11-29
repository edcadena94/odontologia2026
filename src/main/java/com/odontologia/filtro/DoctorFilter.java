package com.odontologia.filtro;

// Importamos las clases de Servlet necesarias
import jakarta.servlet.*;
// Importamos la anotacion WebFilter
import jakarta.servlet.annotation.WebFilter;
// Importamos las clases HTTP
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * CLASE DOCTOR FILTER
 *
 * PARA QUE SIRVE:
 * - Es un FILTRO de seguridad que protege paginas
 * - Verifica que el usuario tenga el rol "doctor" antes de acceder
 * - Si no tiene permiso, lo redirige al login
 *
 * QUE ES UN FILTRO (Filter):
 * - Es como un "guardia de seguridad" que se ejecuta ANTES de que
 *   la peticion llegue al Servlet o JSP
 * - Puede permitir o bloquear el acceso a ciertas paginas
 *
 * COMO FUNCIONA:
 * 1. Usuario intenta acceder a una pagina protegida
 * 2. El filtro intercepta la peticion
 * 3. Verifica si hay sesion y si el rol es "doctor"
 * 4. Si es doctor -> permite el acceso (chain.doFilter)
 * 5. Si NO es doctor -> redirige a login.jsp
 *
 * ANOTACION @WebFilter:
 * - Define que URLs protege este filtro
 * - Ejemplo: @WebFilter("/panelDoctor/*") protege todo lo que empiece con /panelDoctor/
 * - Actualmente esta vacio "" (no protege nada, debes configurarlo)
 *
 * COMO CONFIGURAR:
 * - @WebFilter("/panelDoctor/*") -> Protege el panel del doctor
 * - @WebFilter("/admin/*") -> Protegeria area de admin
 * - @WebFilter({"/pagina1.jsp", "/pagina2.jsp"}) -> Protege paginas especificas
 */
@WebFilter("")  // TODO: Configurar las URLs a proteger, ejemplo: "/panelDoctor/*"
public class DoctorFilter implements Filter {

    /**
     * METODO doFilter
     *
     * Este metodo se ejecuta CADA VEZ que alguien intenta acceder
     * a una URL protegida por este filtro
     *
     * @param request La peticion del usuario
     * @param response La respuesta que enviaremos
     * @param chain La cadena de filtros (para continuar si todo esta bien)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Convertimos el request generico a HttpServletRequest
        // para poder acceder a metodos HTTP como getSession()
        HttpServletRequest req = (HttpServletRequest) request;

        // Obtenemos la sesion del usuario
        // getSession(false) -> NO crea sesion nueva si no existe, retorna null
        // getSession(true) -> SI crea sesion nueva si no existe
        HttpSession session = req.getSession(false);

        // Obtenemos el rol del usuario desde la sesion
        // Si no hay sesion, rol sera null
        String rol = (session != null) ? (String) session.getAttribute("rol") : null;

        // Verificamos si el usuario tiene el rol "doctor"
        if (rol == null || !rol.equals("doctor")) {
            // NO tiene permiso -> Redirigir a login
            // getContextPath() obtiene la ruta base de la aplicacion
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/login.jsp");
            return; // Importante: terminar aqui, no continuar
        }

        // SI tiene permiso -> Continuar con la peticion
        // chain.doFilter permite que la peticion llegue al Servlet/JSP destino
        chain.doFilter(request, response);
    }
}
