package com.example.optisolassesment.utils

import com.example.optisolassesment.model.Incidents
import com.example.optisolassesment.utils.DataUtil.incidentArrayList

fun List<Incidents>.getListData(): ArrayList<Incidents> {
    val incidentList = ArrayList<Incidents>()
    this.forEach { incident ->
        if (!incidentArrayList.contains(incident)) {
            incidentList.add(incident)
        }
    }
    return incidentList
}

object DataUtil {
    val incidentArrayList = ArrayList<Incidents>()

    fun getIncidentData(id: String): Incidents? {
        var incident: Incidents? = null
        for (incidents in incidentArrayList) {
            if (incidents.id.toString() == id)
                incident = incidents
        }
        return incident
    }
}