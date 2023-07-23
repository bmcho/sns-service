package com.service.sns.model.event;

import com.service.sns.model.AlarmArgs;
import com.service.sns.model.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmEvent {

    private int receiveUserId;
    private AlarmType alarmType;
    private AlarmArgs args;
}
