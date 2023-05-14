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

package io.gbloch.meal.order.presentation.adapter.primary;

import io.gbloch.meal.order.application.dto.create.CreateOrderCommand;
import io.gbloch.meal.order.application.dto.create.CreateOrderResponse;
import io.gbloch.meal.order.application.dto.track.TrackOrderQuery;
import io.gbloch.meal.order.application.dto.track.TrackOrderResponse;
import io.gbloch.meal.order.application.port.input.order.CreateOrderUseCase;
import io.gbloch.meal.order.application.port.input.order.TrackOrderUseCase;
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
import lombok.extern.slf4j.Slf4j;

/**
 * OrderResource.
 *
 * @author Gaëtan Bloch
 * <br>Created on 14/05/2023
 */
@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
@Path("api/v1/orders")
@Produces("application/vnd.api.v1+json")
@Consumes(MediaType.APPLICATION_JSON)
public final class OrderResource {

    private final CreateOrderUseCase createOrderUseCase;
    private final TrackOrderUseCase trackOrderUseCase;

    @POST
    public Response createOrder(CreateOrderCommand createOrderCommand) {
        log.info("Received create order request: {}", createOrderCommand);
        log.info(
            "Creating order for customer [{}] at restaurant [{}]",
            createOrderCommand.customerId(),
            createOrderCommand.restaurantId()
        );
        CreateOrderResponse createOrderResponse = createOrderUseCase.createOrder(
            createOrderCommand
        );
        log.info("Order created: {}", createOrderResponse);
        return Response.ok(createOrderResponse).build();
    }

    @Path("/{trackingId}")
    @GET
    public Response trackOrder(@PathParam("trackingId") UUID trackingId) {
        TrackOrderResponse trackOrderResponse = trackOrderUseCase.trackOrder(
            TrackOrderQuery.builder().orderTrackingId(trackingId).build()
        );
        log.info("Returning order status for tracking id [{}]: {}", trackingId, trackOrderResponse);
        return Response.ok(trackOrderResponse).build();
    }
}
