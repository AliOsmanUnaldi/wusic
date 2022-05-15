package com.estu.wusic.business.requests.leaveRoomRequest;

import com.estu.wusic.business.requests.commentRequests.CreateCommentRequest;
import com.estu.wusic.business.requests.pointRequests.CreatePointRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRoomRequest {

    private CreatePointRequest createPointRequest;

    private CreateCommentRequest createCommentRequest;
}
