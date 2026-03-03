package com.example.rsv_stomp_stack.dto;

//record는 () 안에 정의된 필드들을 자동으로 final로 만들어주는 Java 16 이상의 기능입니다.
public record RobotCommandDTO (
    String robotId,
    String command, // 예: "MOVE_FORWARD", "TURN_LEFT", "TURN_RIGHT", "STOP"
    double value   //
){}

