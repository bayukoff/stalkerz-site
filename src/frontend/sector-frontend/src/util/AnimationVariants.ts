export const containerShow = (minHeight: string = '0') => {
    return {
        hidden: {
            opacity: 0,
            height: 0,
            minHeight: minHeight
        },
        visible: {
            height: 'auto',
            minHeight: minHeight ? minHeight : 'auto',
            opacity: 1,
            transition: {
                opacity: {duration: 0.2},
                height: {duration: 0.2},
                minHeight: {duration: 0.2},
                when: 'beforeChildren',
            }
        }
    }
}

export const childrenShow = () => {
    return {
        hidden: {
            opacity: 0,
            height: 0,
        },
        visible: {
            height: 'auto',
            opacity: 1,
            transition: {
                opacity: {duration: 0.2, delay: 0.4},
                height: {duration: 2, delay: 0.4},
            }
        }
    }
}