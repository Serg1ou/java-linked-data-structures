plugins {
    alias(libs.plugins.jagr)
    alias(libs.plugins.algomate)
}

exercise {
    assignmentId.set("h10")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "sh63vika"
    firstName = "Sergio"
    lastName = "Huarcaya Maldonado"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

configurations.all {
    resolutionStrategy {
        configurations.all {
            resolutionStrategy {
                force(
                    libs.algoutils.student,
                    libs.algoutils.tutor,
                    libs.junit.pioneer,
                )
            }
        }
    }
}

jagr {
    graders {
        val graderPublic by getting {
            graderName.set("H10-Public")
            rubricProviderName.set("h10.H10_RubricProviderPublic")
        }
    }
}
