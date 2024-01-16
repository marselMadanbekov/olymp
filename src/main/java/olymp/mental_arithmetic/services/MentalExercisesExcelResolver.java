package olymp.mental_arithmetic.services;

import olymp.mental_arithmetic.model.entities.MentalTask;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MentalExercisesExcelResolver {

    @Value("${uploads_path}")
    private String uploadDir;
    public List<MentalTask> pullMentalTasksFromExcel(String filename) {
        try {
            FileInputStream file = new FileInputStream(uploadDir + "/" + filename);
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);


            List<MentalTask> tasks = new ArrayList<>();
            List<MentalTask> currentTableTasks = null;
            boolean inTable = false;
            for (Row row : sheet) {
                boolean rowEmpty = true;
                for (Cell cell : row) {
                    if (cell.getCellType() != CellType.BLANK) {
                        rowEmpty = false;
                        break;
                    }
                }

                if (rowEmpty) {
                    if (inTable) {
                        currentTableTasks.forEach(task -> {
                            task.setAnswer(task.getTaskEntry().stream().mapToDouble(Double::doubleValue).sum());
                        });
                        tasks.addAll(currentTableTasks);
                        inTable = false;
                    }
                    continue;
                }

                if (!inTable) {
                    currentTableTasks = new ArrayList<>();
                    for (int i = 0; i < row.getLastCellNum() - row.getFirstCellNum(); i++) {
                        currentTableTasks.add(new MentalTask());
                    }
                    inTable = true;
                    continue;
                }

                int colNum = 0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        currentTableTasks.get(colNum).getTaskEntry().add(cell.getNumericCellValue());
                        colNum++;
                    }
                }
            }

            if (currentTableTasks != null) {
                currentTableTasks.forEach(task -> {
                    task.setAnswer(task.getTaskEntry().stream().mapToDouble(Double::doubleValue).sum());
                });
                tasks.addAll(currentTableTasks);
            }
            file.close();
            workbook.close();
            return tasks;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Произошла ошибка при обработке файла с заданиями");
        }
    }
}
