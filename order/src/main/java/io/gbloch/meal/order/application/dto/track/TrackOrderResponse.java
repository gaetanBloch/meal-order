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

package io.gbloch.meal.order.application.dto.track;

import io.gbloch.meal.domain.vo.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

/**
 * TrackOrderResponse.
 *
 * @author Gaëtan Bloch
 * <br>Created on 13/05/2023
 */
@Builder
public record TrackOrderResponse(
    @NotNull UUID orderTrackingId,
    @NotNull OrderStatus orderStatus,
    @NotNull List<String> errorMessages
) {}
