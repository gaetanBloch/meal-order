/*
 * Copyright (c) 2023 Gaëtan Bloch and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.gbloch.meal.customer.presentation.adpater.primary;

import io.gbloch.meal.customer.application.dto.CreateCustomerCommand;
import io.gbloch.meal.customer.application.dto.CreateCustomerResponse;
import io.gbloch.meal.customer.application.dto.GetCustomerResponse;
import io.gbloch.meal.customer.application.port.input.CreateCustomerUseCase;
import io.gbloch.meal.customer.application.port.input.GetCustomerUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

/**
 * OrderResource.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
@Path("api/v1/customers")
@Produces("application/vnd.api.v1+json")
@Consumes(MediaType.APPLICATION_JSON)
public final class CustomerResource {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CustomerResource.class);
    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    @POST
    public Response createCustomer(CreateCustomerCommand createCustomerCommand) {
        log.info("Received create order request: {}", createCustomerCommand);
        log.info("Creating customer with username [{}]", createCustomerCommand.userName());
        CreateCustomerResponse response = createCustomerUseCase.createCustomer(
            createCustomerCommand
        );
        log.info("Order created: {}", response);
        return Response.ok(response).build();
    }

    @Path("/{customerId}")
    @GET
    public Response getCustomer(@PathParam("customerId") UUID customerId) {
        log.info("Received get customer request: {}", customerId);
        GetCustomerResponse response = getCustomerUseCase.getCustomer(customerId);
        log.info("Customer found: {}", response);
        return Response.ok(response).build();
    }
}
