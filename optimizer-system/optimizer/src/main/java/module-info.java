module optimizer {
    exports Interface;
    provides Interface.Optimization with  Implementation.SearchOptimization.SearchOptimization;
    provides Interface.OptimizationAlgorithm with Implementation.SearchOptimization.BruteForceSearch,
            Implementation.SearchOptimization.BoyerMooreSearch;
    uses Interface.OptimizationAlgorithm;
}