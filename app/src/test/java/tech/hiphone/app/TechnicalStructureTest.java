package tech.hiphone.app;

import static com.tngtech.archunit.base.DescribedPredicate.alwaysTrue;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.belongToAnyOf;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import tech.hiphone.commons.config.ApplicationProperties;
import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.commons.constants.CommonsConstants;
import tech.hiphone.commons.constants.ErrorCodeContants;
import com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packagesOf = Application.class, importOptions = DoNotIncludeTests.class)
class TechnicalStructureTest {

    // prettier-ignore
    // @formatter:off
    @ArchTest
    static final ArchRule respectsTechnicalArchitectureLayers = layeredArchitecture()
        .layer("Config").definedBy("..config..")
        .layer("Web").definedBy("..web..")

        .whereLayer("Config").mayNotBeAccessedByAnyLayer()
        .whereLayer("Web").mayOnlyBeAccessedByLayers("Config")

        .ignoreDependency(belongToAnyOf(Application.class), alwaysTrue())
        .ignoreDependency(alwaysTrue(), belongToAnyOf(
            CommonsConstants.class,
            ErrorCodeContants.class,
            AuthoritiesConstants.class,
            ApplicationProperties.class
        ));
    // @formatter:on
}
