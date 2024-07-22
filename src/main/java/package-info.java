/**
 * This package contains the main documents related to the project.
 *
 * <p>
 * The database diagram file is included, which outlines the structure and relationships
 * of the database tables.
 * </p>
 *
 * <p>
 * The JSON document provided is version 2.1. Note the following specifics:
 * </p>
 * <ul>
 *   <li>For POST requests, there are hard-coded values.</li>
 *   <li>For all other paths, parameters are collection-based.</li>
 * </ul>
 *
 * <p>
 * All the APIs in this project are authentication-based. Before invoking any API,
 * please obtain a JWT token by sending a POST request to {@code {{host}}/api/v1/auth/token}.
 * Use Basic authentication with your username and password to get the token.
 * </p>
 */
package com.thilina.booking_manager.document;