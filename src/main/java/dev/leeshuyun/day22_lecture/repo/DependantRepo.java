package dev.leeshuyun.day22_lecture.repo;

import java.util.List;

import dev.leeshuyun.day22_lecture.model.Employee;
import dev.leeshuyun.day22_lecture.model.Dependant;


public interface DependantRepo {
    List<Dependant> retrieveDependantList();
}
