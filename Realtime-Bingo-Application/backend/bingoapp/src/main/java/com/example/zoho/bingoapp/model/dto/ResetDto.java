package com.example.zoho.bingoapp.model.dto;

import lombok.*;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetDto {
    private ArrayList<ArrayList<Integer>> mat;
}
