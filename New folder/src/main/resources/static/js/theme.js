(function () {
    var STORAGE_KEY = 'study-planner-theme-mode';
    var media = window.matchMedia('(prefers-color-scheme: dark)');

    function readSavedMode() {
        var saved = window.localStorage.getItem(STORAGE_KEY);
        if (saved === 'light' || saved === 'dark' || saved === 'system') {
            return saved;
        }
        return 'system';
    }

    function resolveTheme(mode) {
        if (mode === 'system') {
            return media.matches ? 'dark' : 'light';
        }
        return mode;
    }

    function applyTheme(mode) {
        var theme = resolveTheme(mode);
        document.documentElement.classList.toggle('dark', theme === 'dark');
        document.documentElement.setAttribute('data-theme-mode', mode);
    }

    function updateAllSelectors(mode) {
        var selectors = document.querySelectorAll('[data-theme-select]');
        selectors.forEach(function (select) {
            select.value = mode;
        });
    }

    function setMode(mode) {
        window.localStorage.setItem(STORAGE_KEY, mode);
        applyTheme(mode);
        updateAllSelectors(mode);
    }

    var initialMode = readSavedMode();
    applyTheme(initialMode);

    document.addEventListener('DOMContentLoaded', function () {
        updateAllSelectors(initialMode);

        var selectors = document.querySelectorAll('[data-theme-select]');
        selectors.forEach(function (select) {
            select.addEventListener('change', function (event) {
                var mode = event.target.value;
                if (mode !== 'light' && mode !== 'dark' && mode !== 'system') {
                    return;
                }
                setMode(mode);
            });
        });
    });

    media.addEventListener('change', function () {
        var currentMode = readSavedMode();
        if (currentMode === 'system') {
            applyTheme(currentMode);
        }
    });
})();
