package com.service.sns.model;

import com.service.sns.model.entity.AlarmEntity;
import com.service.sns.model.enums.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Alarm {

    private int id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp registerAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static Alarm fromEntity(AlarmEntity alarmEntity) {
        return new Alarm(
                alarmEntity.getId(),
                alarmEntity.getAlarmType(),
                alarmEntity.getArgs(),
                alarmEntity.getRegisteredAt(),
                alarmEntity.getUpdatedAt(),
                alarmEntity.getDeleteAt()
        );
    }

}
